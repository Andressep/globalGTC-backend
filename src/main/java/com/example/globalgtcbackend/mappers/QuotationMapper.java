package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductDTO;
import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;
import com.example.globalgtcbackend.models.entity.QuotationDetails;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuotationMapper {

    private final ModelMapper modelMapper;

    public QuotationMapper() {
        modelMapper = new ModelMapper();
    }

    public QuotationDTO toDTO(Quotation quotation) {
        QuotationDTO quotationDTO = modelMapper.map(quotation, QuotationDTO.class);

        // Llena los atributos adicionales de quotationDTO
        quotationDTO.setCustomerName(quotation.getCustomer().getName());
        quotationDTO.setCustomerPhoneNumber(quotation.getCustomer().getPhone());
        quotationDTO.setCustomerAddress(quotation.getCustomer().getAddress());
        quotationDTO.setCustomerRut(quotation.getCustomer().getRut());
        quotationDTO.setSalespersonName(quotation.getSalesperson().getName());

        // Llena los detalles de la cotización
        List<ProductQuotationDTO> productQuotationDTOList = new ArrayList<>();
        for (QuotationDetails details : quotation.getQuotationDetails()) {
            ProductQuotationDTO productQuotationDTO = new ProductQuotationDTO();
            productQuotationDTO.setProductQuotationId(details.getItemId());
            productQuotationDTO.setProductQuotationId(details.getProduct().getProductId());
            productQuotationDTO.setProductCode(details.getProduct().getCode());
            productQuotationDTO.setDescription(details.getProduct().getDescription());
            productQuotationDTO.setQuantity(details.getQuantity());
            productQuotationDTO.setPrice(details.getProduct().getPrice());
            productQuotationDTO.setLength(details.getProduct().getLength());
            // Calcula el total del producto (cantidad x precio)
            productQuotationDTO.setTotalPrice(details.calculate());
            // Agrega el producto a la lista
            productQuotationDTOList.add(productQuotationDTO);
        }
        quotationDTO.setQuotationDetailsList(productQuotationDTOList);

        // Calcula el subtotal, impuesto y total del peso
        double subTotal = 0;
        double tax = 0;
        double totalWeight = 0;
        double totalPayment = 0;
        for (ProductQuotationDTO product : productQuotationDTOList) {
            subTotal += product.getTotalPrice();
            // Calcula el impuesto como un porcentaje del subtotal (ajusta esto según tus necesidades)
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
