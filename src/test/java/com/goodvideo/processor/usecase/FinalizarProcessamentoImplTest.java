package com.goodvideo.processor.usecase;

import com.goodvideo.processor.domains.Status;
import com.goodvideo.processor.gateways.FinalizarProcessamentoMensageriaGateway;
import com.goodvideo.processor.gateways.messaging.kafka.resources.FinalizarProcessamentoMensagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class FinalizarProcessamentoImplTest {

    private FinalizarProcessamentoMensageriaGateway mockGateway;
    private FinalizarProcessamentoImpl finalizarProcessamento;

    @BeforeEach
    void setUp() {
        // Mock the gateway dependency
        mockGateway = mock(FinalizarProcessamentoMensageriaGateway.class);

        // Create the class under test with the mocked dependency
        finalizarProcessamento = new FinalizarProcessamentoImpl(mockGateway);
    }

    @Test
    void testExecutar_CallsGatewayPublish() {
        // Arrange: Create a dummy message to test with
        FinalizarProcessamentoMensagem message = new FinalizarProcessamentoMensagem("video123", Status.CONCLUIDO, "Process completed");

        // Act: Call the method under test
        finalizarProcessamento.executar(message);

        // Assert: Verify that the gateway's publish method was called with the correct argument
        verify(mockGateway, times(1)).publicar(message);
    }
}