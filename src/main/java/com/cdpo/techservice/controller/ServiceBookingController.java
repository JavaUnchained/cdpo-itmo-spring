package com.cdpo.techservice.controller;

import com.cdpo.techservice.service.IServiceBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/service/booking")
public class ServiceBookingController extends BaseController<IServiceBookingService> {

    @Autowired
    public ServiceBookingController(IServiceBookingService iService) {
        super(iService);
    }
}
