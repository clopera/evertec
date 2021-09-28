package com.pruebatec.utilities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public class TratamientoTexto {
    public static Integer CANT_REGISTROSXPAGINAS_DEFAULT=50;

    public static Pageable getPagination(Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("page", "0"));
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", CANT_REGISTROSXPAGINAS_DEFAULT.toString()));
        Pageable pageable = PageRequest.of(page,pageSize);

        return pageable;
    }
    public Pageable getPagination() {
        Pageable pageable = PageRequest.of(0,9999999);

        return pageable;
    }

    public static Long getFilterLong(Map<String, String> params, String key) {
        String value = params.get(key);
        if (value == null || "".equals(value)) {
            return null;
        }

        try {
            Long d = Long.parseLong(value);
            return d;
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    public static  String getFilterText(Map<String, String> params) {
        String defaultFilter = params.getOrDefault("defaultFilter", "%");
        String filter = params.getOrDefault("filtro", defaultFilter);
        if (defaultFilter == "%") {
            filter = filter.equals("") ? "%" : "%" + filter + "%";
            filter = filter.replace(" ", "%");
        }

        return filter;
    }

}
