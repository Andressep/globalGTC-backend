package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Quotation;
import com.example.globalgtcbackend.models.entity.QuotationDetails;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class QuotationMapper {

    private final ModelMapper modelMapper;

    public QuotationMapper() {
        modelMapper = new ModelMapper();
    }

    public QuotationDTO toDTO(Quotation quotation) {
        QuotationDTO quotationDTO = modelMapper.map(quotation, QuotationDTO.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("es", "ES")); // Define tu formato aquí
        String formattedDate = quotation.getCreatedAt().format(formatter);
        quotationDTO.setCreatedAt(formattedDate);

        quotationDTO.setCustomerName(quotation.getCustomer().getName());
        quotationDTO.setCustomerPhoneNumber(quotation.getCustomer().getPhone());
        quotationDTO.setCustomerAddress(quotation.getCustomer().getAddress());
        quotationDTO.setCustomerRut(quotation.getCustomer().getRut());
        quotationDTO.setSalespersonName(quotation.getSalesperson().getName());

        List<ProductQuotationDTO> productQuotationDTOList = new ArrayList<>();
        for (QuotationDetails details : quotation.getQuotationDetails()) {
            ProductQuotationDTO productQuotationDTO = new ProductQuotationDTO();

            productQuotationDTO.setProductQuotationId(details.getProduct().getProductId());
            productQuotationDTO.setProductCode(details.getProduct().getCode());
            productQuotationDTO.setDescription(details.getProduct().getDescription());
            productQuotationDTO.setQuantity(details.getQuantity());
            productQuotationDTO.setPrice(details.getProduct().getPrice());
            productQuotationDTO.setWeightPerMeter(details.getProduct().getWeight());
            productQuotationDTO.setLength(details.getProduct().getLength());
            productQuotationDTO.setTotalPrice(details.calculate());

            productQuotationDTOList.add(productQuotationDTO);
        }
        quotationDTO.setQuotationDetailsList(productQuotationDTOList);

        double subTotal = 0;
        double tax = 0;
        double totalWeight = 0;
        double totalPayment = 0;
        for (ProductQuotationDTO product : productQuotationDTOList) {

            subTotal += product.getTotalPrice();
            tax += (0.19 * subTotal);
            totalWeight += (product.getQuantity() * product.getWeightPerMeter());
            totalPayment += (tax + subTotal);
        }
        quotationDTO.setSubTotal(subTotal);
        quotationDTO.setTax(tax);
        quotationDTO.setTotalWeight(totalWeight);
        quotationDTO.setTotalPayment(totalPayment);

        return quotationDTO;
    }

    public List<QuotationDTO> toDTOList(List<Quotation> quotationList) {
        return quotationList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
