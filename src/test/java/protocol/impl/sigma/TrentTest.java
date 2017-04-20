package protocol.impl.sigma;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import crypt.factories.ElGamalAsymKeyFactory;
import model.entity.ElGamalKey;
import model.entity.sigma.ResEncrypt;
import model.entity.sigma.ResponsesCCD;
import util.TestInputGenerator;

/**
 * Trent unit tests
 * @author denis.arrivault[@]univ-amu.fr
 */
public class TrentTest {
	private byte[] msg;
	ElGamalKey key;
	Trent trent;
	
	@Before
	public void instanciate(){
		msg = TestInputGenerator.getRandomBytes(50);
		key = ElGamalAsymKeyFactory.create(false);
		trent = new Trent(key);
	}
	
	@Test
	public void getterTest(){
		assertTrue(trent.getKey() == key);
	}
	
	@Test
	public void sendResponseTest() {
		ElGamalEncrypt encrypt = (new ElGamal(key)).encryptForContract(msg);
		ResEncrypt res = new ResEncrypt(encrypt.getU(), encrypt.getV(), msg);
		ResponsesCCD response = trent.SendResponse(res);
		assertTrue(response.Verifies(key, res));
	}
	
	@Test
	public void decryptionTest(){
		byte[] encrypted = (new ElGamal(key)).encryptWithPublicKey(msg);		
		byte[] decryptedMsg = trent.decryption(encrypted);
		assertArrayEquals(decryptedMsg, msg);
	}
	
}

