package com.example.globalgtcbackend.mappers;

import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Quotation;
import com.example.globalgtcbackend.models.entity.QuotationDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component("QuotationMapper")
public class QuotationMapper implements Mapper<Quotation, QuotationDTO> {

    @Override
    public Quotation transformBack(QuotationDTO source) {
        var quotation = new Quotation();
        BeanUtils.copyProperties(source, quotation);
        return quotation;
    }
    @Override
    public QuotationDTO transform(Quotation source) {
        QuotationDTO quotationDTO = new QuotationDTO();
        BeanUtils.copyProperties(source, quotationDTO, "quotationDetails");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("es", "ES")); // Define tu formato aquí
        String formattedDate = source.getCreatedAt().format(formatter);
        // Datos asignados //
        quotationDTO.setCreatedAt(formattedDate);
        quotationDTO.setCustomerName(source.getCustomer().getName());
        quotationDTO.setCustomerRut(source.getCustomer().getRut());
        quotationDTO.setCustomerAddress(source.getCustomer().getAddress());
        quotationDTO.setCustomerPhoneNumber(source.getCustomer().getPhone());
        quotationDTO.setSalespersonName(source.getSalesperson().getName());

        // Obteniendo datos de los productos //
        List<ProductQuotationDTO> productList = new ArrayList<>();
        for (QuotationDetails details : source.getQuotationDetails()) {
            ProductQuotationDTO productDTO = new ProductQuotationDTO();

            productDTO.setProductId(details.getProduct().getProductId());
            productDTO.setProductCode(details.getProduct().getProductCode());
            productDTO.setDescription(details.getProduct().getDescription());
            productDTO.setCategory(details.getProduct().getCategory());
            productDTO.setQuantity(details.getQuantity());
            productDTO.setPrice(details.getProduct().getPrice());
            productDTO.setWeightPerMeter(details.getProduct().getWeightPerMeter());
            productDTO.setLength(details.getProduct().getLength());
            productDTO.setTotalPrice(details.calculate());
            productList.add(productDTO);
        }
        quotationDTO.setQuotationDetailsList(productList);

        // Obteniendo datos acumulativos de la cotizacion //
        double subTotal = 0;
        double tax = 0;
        double totalWeight = 0;
        DecimalFormat df = new DecimalFormat("#.##");

        for (ProductQuotationDTO product : productList) {
            subTotal += product.getTotalPrice();
            totalWeight += (product.getQuantity() * (product.getWeightPerMeter() * product.getLength()));
        }
        tax = subTotal * 0.19;
        quotationDTO.setSubTotal(subTotal);
        quotationDTO.setTax(tax);
        quotationDTO.setTotalWeight(Double.parseDouble(df.format(totalWeight)));
        quotationDTO.setTotalPayment(subTotal + tax);

        return quotationDTO;
    }
}
