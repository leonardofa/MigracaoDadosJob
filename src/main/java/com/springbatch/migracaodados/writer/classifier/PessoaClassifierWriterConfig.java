package com.springbatch.migracaodados.writer.classifier;

import com.springbatch.migracaodados.dominio.Pessoa;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaClassifierWriterConfig {

    @Bean
    public ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter(
            JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
            FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWrite
    ) {
        return new ClassifierCompositeItemWriterBuilder<Pessoa>()
                .classifier(pessoa -> pessoa.isValida() ? bancoPessoaWriter : arquivoPessoasInvalidasWrite)
                .build();
    }

}
