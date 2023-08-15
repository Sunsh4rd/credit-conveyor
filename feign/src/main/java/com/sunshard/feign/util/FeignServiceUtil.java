package com.sunshard.feign.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "demo feign", url = "http://localhost:8080/conveyor/offers")
public class FeignServiceUtil {
    @PostMapping("/conveyor/offers")

}
