package com.sameer.zapurl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClickEventDTO {
    private LocalDate clickDate;
    private Long count;
}
