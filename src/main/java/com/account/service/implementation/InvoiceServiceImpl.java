package com.account.service.implementation;

import com.account.dto.ClientVendorDto;
import com.account.dto.InvoiceDto;
import com.account.dto.InvoiceProductDto;
import com.account.dto.ProductDto;
import com.account.entity.ClientVendor;
import com.account.entity.Invoice;
import com.account.enums.ClientVendorType;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.mapper.MapperUtil;
import com.account.repository.InvoiceRepository;
import com.account.service.InvoiceProductService;
import com.account.service.InvoiceService;
import com.account.service.ProductService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final SecurityService securityService;
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;


    public InvoiceServiceImpl(SecurityService securityService, InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, ProductService productService) {
        this.securityService = securityService;
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
    }


    // list should be sorted by their invoice no in descending order (the latest invoices should be at the top)
    @Override
    public List<InvoiceDto> findAllByCompany(InvoiceType purchase) {
        Long companyId = securityService.getLoggedInUser().getCompany().getId();

        return invoiceRepository.findAllByCompanyId(companyId)
                .stream()
                .filter(invoice -> invoice.getInvoiceType().equals(InvoiceType.PURCHASE))
                .sorted(Comparator.comparing(Invoice::getInvoiceNo)
                        .thenComparing(Invoice::getDate).reversed())
                .map((Invoice invoice)-> {
                    InvoiceDto invoiceDto = mapperUtil.convertToType(invoice, new InvoiceDto());
                    invoiceDto.setPrice(getInvoiceTotalPrice(invoiceDto.getId()));
                    invoiceDto.setTax(getInvoiceTotalTaxOf(invoiceDto.getId()));
                    invoiceDto.setTotal(getInvoiceTotalPrice(invoiceDto.getId()).add(getInvoiceTotalTaxOf(invoiceDto.getId())));

                    return invoiceDto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> getAllByCompanyAndType(ClientVendorType vendor) {
        Long id = securityService.getLoggedInUser().getCompany().getId();
        return invoiceRepository.findAllByCompanyId(id)
                .stream()
                .map(Invoice::getClientVendor)
                .distinct()
                .filter(clientVendor -> clientVendor.getClientVendorType().equals(vendor))
                .map(clientVendor->mapperUtil.convertToType(clientVendor, new ClientVendorDto()))
                .sorted(Comparator.comparing(ClientVendorDto::getClientVendorName))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDto createInvoiceDto(InvoiceType invoiceType) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setInvoiceNo(invoiceNoGenerator(invoiceType));
        invoiceDto.setDate(LocalDate.now());
        invoiceDto.setCompanyDto(securityService.getLoggedInUser().getCompany());
        return invoiceDto;
    }

    @Override
    public InvoiceDto saveInvoice(InvoiceDto invoiceDto, InvoiceType invoiceType) {
        invoiceDto.setCompanyDto(securityService.getLoggedInUser().getCompany());
        invoiceDto.setInvoiceType(invoiceType);
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        Invoice invoice = mapperUtil.convertToType(invoiceDto, new Invoice());
        invoiceRepository.save(invoice);
        return mapperUtil.convertToType(invoice,invoiceDto);
    }

    @Override
    public InvoiceDto findInvoiceById(Long id) {
        return mapperUtil.convertToType(invoiceRepository.findById(id).orElseThrow(),new InvoiceDto());
    }

    @Override
    public List<InvoiceProductDto> getInvoiceProductsByInvoiceId(Long invoiceId) {

        return invoiceProductService.findInvoiceProductByInvoiceId(invoiceId);
    }

    @Override
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto, InvoiceType type) {
        Invoice invoice = invoiceRepository.findById(invoiceDto.getId()).orElseThrow();
        ClientVendor clientVendorToBeSaved = mapperUtil.convertToType(invoiceDto.getClientVendor(),new ClientVendor());
        invoice.setClientVendor(clientVendorToBeSaved);
       return mapperUtil.convertToType( invoiceRepository.save(invoice), new InvoiceDto());
    }

    @Override
    public void deleteById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
        invoice.setIsDeleted(true);

        invoiceProductService.findByInvoiceId(invoice.getId())
                .forEach(invoiceProductDto -> invoiceProductService.deleteInvoiceProductById(invoiceProductDto.getId()));

        invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceProductDto addInvoiceProduct(Long invoiceId, InvoiceProductDto invoiceProductDto) {

        InvoiceDto invoiceDto = this.findInvoiceById(invoiceId);
        invoiceProductDto.setInvoice(invoiceDto);
        return invoiceProductService.saveInvoiceProductDto(invoiceProductDto);
    }

    @Override
    public void removeInvoiceProductById(Long invoiceProductId) {
        invoiceProductService.deleteInvoiceProductById(invoiceProductId);

    }

    @Override
    public void approvePurchaseInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice is not found"));
        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoice.setDate(LocalDate.now());
        updateQuantityOfInvoiceProducts(invoice);
        invoiceRepository.save(invoice);
    }

    private void updateQuantityOfInvoiceProducts(Invoice invoice) {

        List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.findInvoiceProductByInvoiceId(invoice.getId());
            invoiceProductDtoList.forEach((InvoiceProductDto invoiceProductDto)->{
                ProductDto productDto = invoiceProductDto.getProduct();
                Integer currentQuantityInStock = productDto.getQuantityInStock();
                if(invoice.getInvoiceType().equals(InvoiceType.PURCHASE)){
                    productDto.setQuantityInStock(currentQuantityInStock + invoiceProductDto.getQuantity());
                }else{
                    productDto.setQuantityInStock(currentQuantityInStock-invoiceProductDto.getQuantity());
                }
               productService.save(productDto);
            });







//        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findInvoiceProductsByInvoice(invoice);
//        for (InvoiceProduct invoiceProduct : invoiceProductList) {
//            Product product = invoiceProduct.getProduct();
//            int currentQuantity = product.getQuantityInStock();
//            if(invoice.getInvoiceType() == InvoiceType.PURCHASE) {
//                product.setQuantityInStock(currentQuantity + invoice Product.getQuantity());
//            }else{
//                product.setQuantityInStock(currentQuantity - invoiceProduct.getQuantity());
//            }
//            productRepository.save(product);
//        }
    }


    private String invoiceNoGenerator(InvoiceType invoiceType) {
        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        List<Invoice> invoiceList = invoiceRepository.findAllByCompanyIdAndInvoiceType(companyId, invoiceType);
        if (invoiceList.size() == 0) {
            return invoiceType.name().charAt(0) + "-001";
        }
        Invoice lastCreatedInvoiceOfTheCompany = invoiceList.stream()
                .max(Comparator.comparing(Invoice::getInsertDateTime))
                .get();

        int newOrder = Integer.parseInt(lastCreatedInvoiceOfTheCompany.getInvoiceNo().substring(2)) + 1;
        return invoiceType.name().charAt(0) + "-" + String.format("%03d", newOrder);

    }

    private BigDecimal getInvoiceTotalPrice(Long id) {
        return invoiceProductService.findByInvoiceId(id)
                .stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf((long) p.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
    private BigDecimal getInvoiceTotalTaxOf(Long id){
        return invoiceProductService.findByInvoiceId(id).stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf(p.getQuantity() * p.getTax() /100d))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
