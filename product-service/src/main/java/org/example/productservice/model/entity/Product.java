package org.example.productservice.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(value="product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;

    private String generateCode() {
        UUID uuidCode = UUID.randomUUID();
        return uuidCode.toString();
    }

    public Product(String name, String description) {
        this.name = name;
        this.code = generateCode();
        this.description = description;
    }
}
