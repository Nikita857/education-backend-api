package com.bm.education.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class DatabaseSeederRunner implements ApplicationRunner {

    private final DatabaseSeeder databaseSeeder;
    private final PlatformTransactionManager transactionManager;

    @Override
    public void run(ApplicationArguments args) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try {
                databaseSeeder.seedDatabase();
            } catch (Exception e) {
                System.err.println("Error seeding database: " + e.getMessage());
                e.printStackTrace();
                status.setRollbackOnly();
            }
            return null;
        });
    }
}
