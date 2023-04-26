package com.octa.adminservice.configuration;

import com.octa.adminservice.mapper.AdminServiceMapper;
import com.octa.adminservice.port.persistence.IAdminRepository;
import com.octa.adminservice.service.IAdminService;
import com.octa.adminservice.service.impl.AdminServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public IAdminService adminService(AdminServiceMapper adminServiceMapper, IAdminRepository adminRepository){
        return new AdminServiceImpl(adminServiceMapper,adminRepository);
    }
}
