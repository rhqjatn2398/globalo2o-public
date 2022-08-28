package com.example.globalO2O.login;

import com.example.globalO2O.login.domain.community.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Bumsoo
 * @version 1.1, 2022.6.5
 */
@Configuration
public class CommunityConfig {

    private DataSource dataSource;

    @Autowired
    public void communityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
