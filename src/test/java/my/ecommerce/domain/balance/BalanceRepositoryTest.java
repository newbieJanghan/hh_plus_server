package my.ecommerce.domain.balance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class BalanceRepositoryTest {
	@Autowired
	private UserBalanceRepository userBalanceRepository;

	@Test
	@DisplayName("UserBalance 도메인을 저장한 후 영속성 저장된 UserBalance 를 반환 성공")
	public void success_saveAndReturnPersistedUserBalance() {
		UserBalance userBalance = prepareUserBalance();

		// when
		UserBalance result = userBalanceRepository.save(userBalance);

		// then
		assertInstanceOf(UserBalance.class, result);
		assertInstanceOf(UUID.class, result.getId());
		assertNotNull(result.getId());
		assertEquals(userBalance.getUserId(), result.getUserId());
		assertEquals(userBalance.getAmount(), result.getAmount());

		// cleanup
		userBalanceRepository.destroy(result.getId());
	}

	@Test
	@DisplayName("userId 로 UserBalance 를 조회 성공")
	public void success_findByUserId() {
		// given
		UserBalance persistence = userBalanceRepository.save(prepareUserBalance());

		// when
		UserBalance result = userBalanceRepository.findByUserId(persistence.getUserId());

		// then
		assertNotNull(result);
		assertEquals(persistence.getId(), result.getId());
		assertEquals(persistence.getUserId(), result.getUserId());
		assertEquals(persistence.getAmount(), result.getAmount());

		// cleanup
		userBalanceRepository.destroy(persistence.getId());
	}

	@Test
	@DisplayName("userId 로 UserBalance 를 조회 시 로우가 없는 경우 null 반환")
	public void success_findByUserId_withNoRow() {
		// given
		UUID userId = UUIDGenerator.generate();

		// when
		UserBalance result = userBalanceRepository.findByUserId(userId);

		// then
		assertNull(result);
	}

	private UserBalance prepareUserBalance() {
		return UserBalance.newBalance(UUIDGenerator.generate());
	}

}
