package com.liquid.entity;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportSelector Demo类
 *
 * @author Liquid
 */
public class ImportSelectorDemo implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.liquid.entity.ImportDemo2"};
    }
}
