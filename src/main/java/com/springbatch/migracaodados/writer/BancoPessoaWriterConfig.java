package com.springbatch.migracaodados.writer;

import com.springbatch.migracaodados.dominio.Pessoa;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class BancoPessoaWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Pessoa> bancoPessoaWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(dataSource)
                .sql("INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (? , ? , ?, ?, ?)")
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    private ItemPreparedStatementSetter<Pessoa> itemPreparedStatementSetter() {
        return new ItemPreparedStatementSetter<Pessoa>() {

            @Override
            public void setValues(Pessoa pessoa, PreparedStatement ps) throws SQLException {
                int count = 1;
                ps.setInt(count++, pessoa.getId());
                ps.setString(count++, pessoa.getNome());
                ps.setString(count++, pessoa.getEmail());
                ps.setDate(count++, new Date(pessoa.getDataNascimento().getTime()));
                ps.setInt(count++, pessoa.getIdade());
            }
        };
    }
}
