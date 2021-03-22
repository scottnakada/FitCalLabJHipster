package org.jhipster.fitcallab;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.jhipster.fitcallab");

        noClasses()
            .that()
                .resideInAnyPackage("org.jhipster.fitcallab.service..")
            .or()
                .resideInAnyPackage("org.jhipster.fitcallab.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.jhipster.fitcallab.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
