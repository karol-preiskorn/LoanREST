package pl.ultimasolution.loan.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class LoanService {

    public void setDataSource(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    }

/*    public static int Ip4LoanNameCount(String Ip, String loanName){
        String sql = "select count(*) from loans where ip=? and loanName=?";
        return jdbcTemplate.query(sql,
                Ip,
                loanName);
    }*/
}