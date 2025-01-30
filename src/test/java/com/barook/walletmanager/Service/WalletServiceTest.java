package com.barook.walletmanager.Service;

import com.barook.walletmanager.DTO.WalletDto;
import com.barook.walletmanager.Entity.User;
import com.barook.walletmanager.Entity.Wallet;
import com.barook.walletmanager.Repository.UserRepository;
import com.barook.walletmanager.Repository.WalletRepository;
import com.barook.walletmanager.ResponceDTO.WalletResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService; // Inject the mocks into the service class

    private Wallet wallet;
    private User user;
    private WalletDto walletDto;

    @BeforeEach
    void setUp() {
        // Initialize the objects you will use in tests
        user = new User(120,"reza","rezayee");
        wallet = new Wallet(1L, BigDecimal.valueOf(500), user, LocalDateTime.now());
        walletDto = new WalletDto(1L, BigDecimal.valueOf(500));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveWallet() {


    }

    @Test
    void testSaveWallet_Success() {
        // Mock the behavior of userRepository and walletRepository
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        // Call the service method
        Wallet savedWallet = walletService.saveWallet(walletDto);

        // Verify that the method behaves as expected
        assertNotNull(savedWallet);
        assertEquals(walletDto.balance(), savedWallet.getBalance());
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testGetTotalBalanceByPrettyJson() throws JsonProcessingException {
        // Mock the behavior of walletRepository
        List<Long> totalBalance = Arrays.asList(1000L); // Simulating a total balance of 1000
        when(walletRepository.getUserTotalBallance(1)).thenReturn(totalBalance);

        // Call the service method
        String result = walletService.getTotalBalanceByPrettyJson(1);

        // Verify the output
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(Collections.singletonMap("total_balance", 1000));

        assertEquals(expectedJson, result);
    }

    @Test
    void testSaveWallet_UserNotFound() {
        // Mock the behavior of userRepository to return an empty Optional
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Expect an exception to be thrown
        assertThrows(IllegalArgumentException.class, () -> walletService.saveWallet(walletDto));
    }

    @Test
    void get_all_wallet() {
        // given
        List<WalletResDto> walletResDtoList = walletService.getAllWallet();

        // when
        List<Wallet> wallets = walletRepository.findAll();

        // then
        assertNotNull(walletResDtoList);
        assertNotNull(wallets);
        assertEquals(walletResDtoList.size(), wallets.size());
    }

    @Test
    void get_all_wallet_by_user_id() {
        // given
        List<WalletResDto> walletResDtoList = walletService.getAllWalletByUserid(4);

        // when
        List<Wallet> wallets = walletRepository.findByUserId(4);

        // then
        assertNotNull(walletResDtoList);
        assertEquals(walletResDtoList.size(), wallets.size());
    }


    @Test
    void test_get_all_wallet_by_user_id() {
        // Prepare a list of wallets for a specific user
        Wallet wallet2 = new Wallet(2L, BigDecimal.valueOf(1000), user, LocalDateTime.now());
        wallet = new Wallet(1L, BigDecimal.valueOf(500), user, LocalDateTime.now());

        List<Wallet> wallets = Arrays.asList(wallet, wallet2);

        // Mock the behavior of walletRepository
        when(walletRepository.findByUserId(1L)).thenReturn(wallets);

        // Call the service method
        List<WalletResDto> walletResDtos = walletService.getAllWalletByUserid(1L);

        // Verify the result
        assertEquals(2, walletResDtos.size());
        assertEquals(wallet.getId(), walletResDtos.get(0).id());
        assertEquals(wallet2.getId(), walletResDtos.get(1).id());
    }

}