package com.example.globalO2O.sor.config;

import com.example.globalO2O.sor.DAO.AnnounceRepo;
import com.example.globalO2O.sor.DAO.JdbcTemplateAnnounceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ParsingConfig {

    private DataSource dataSource;

    @Autowired
    public void parsingConfig(DataSource dataSource){this.dataSource = dataSource;}

    @Bean
    public AnnounceRepo announceRepo(){
        return new JdbcTemplateAnnounceRepo(dataSource);
    }
}
