package aem.example.tdd.ecasastorage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcasaStorageApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainTest() {
		assertDoesNotThrow(() -> EcasaStorageApplication.main(new String[] {}));
	}

}
