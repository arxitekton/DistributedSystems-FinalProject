package com.ucu.edu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ucu.edu.user.model.PIN;
import com.ucu.edu.security.repository.PINRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootJPAIntegrationTest {
  
    @Autowired
    private PINRepository PINRepository;
 
    @Test
    public void givenPINRepository_whenSaveAndRetreiveEntity_thenOK() {
    	
		PIN.Builder pinBuilder = PIN.getBuilder().pin("test");
		PIN newPIN = pinBuilder.build();

        PIN genericEntity = PINRepository
          .save(newPIN);
        PIN foundEntity = PINRepository
          .findOne(genericEntity.getId());
  
        assertNotNull(foundEntity);
        assertEquals(genericEntity.getPin(), foundEntity.getPin());
    }
}
