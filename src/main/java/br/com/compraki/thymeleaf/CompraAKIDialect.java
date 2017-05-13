package br.com.compraki.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.compraki.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.compraki.thymeleaf.processor.MenuAttributeTagProcessor;
import br.com.compraki.thymeleaf.processor.MessageElementTagProcessor;
import br.com.compraki.thymeleaf.processor.OrderElementTagProcessor;
import br.com.compraki.thymeleaf.processor.PaginationElementTagProcessor;

public class CompraAKIDialect extends AbstractProcessorDialect {

    public CompraAKIDialect() {
        super("Compraki Dialect Validation", "compraki", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
        processors.add(new MessageElementTagProcessor(dialectPrefix));
        processors.add(new OrderElementTagProcessor(dialectPrefix));
        processors.add(new PaginationElementTagProcessor(dialectPrefix));
        processors.add(new MenuAttributeTagProcessor(dialectPrefix));
        return processors;
    }

}
