package com.tdt.historical_prices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseMetaData {
    private Object meta;
    private Object data;

    public ResponseMetaData(Object meta, Object data) {
        this.meta = meta;
        this.data = data;
    }

    public ResponseMetaData(MetaDTO metaDTO, Object data) {
        this.setMeta(metaDTO);
        this.data = data;
    }

    public ResponseMetaData(List<MetaDTO> metaDTO, Object data) {
        this.setMeta(metaDTO);
        this.data = data;
    }
}
