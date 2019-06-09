package com.dataart.warehouse.services;

import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.Task;
import com.dataart.warehouse.repository.LoaderRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskManagementServiceTest {
    private static Integer LOADER_ID = 15;

    @Mock
    private LoaderRepo loaderRepo;

    @Mock
    private FindTaskFromGateStrategy findTaskFromGateStrategy;
    @Mock
    private FindTaskFromBufferZoneStrategy findTaskFromBufferZoneStrategy;
    @Mock
    private FindTaskFromFirstZoneStrategy findTaskFromFirstZoneStrategy;
    @Mock
    private List<FindTaskStrategy> findTaskStrategies;

    @InjectMocks
    private TaskManagementService taskManagementService;

    @Test
    public void shouldNotAnyTaskForLoader() {
        Task task = taskManagementService.findTaskForLoader(LOADER_ID);
        Assert.assertNull(task);
        Mockito.verify(loaderRepo, Mockito.times(1)).findTopById(LOADER_ID);
    }

    @Test
    public void shouldSomeTaskForLoader() {
        Mockito.doReturn(new Loader())
                .when(loaderRepo)
                .findTopById(LOADER_ID);

        Loader loader = loaderRepo.findTopById(LOADER_ID);
        Mockito.verify(loaderRepo, Mockito.times(1)).findTopById(LOADER_ID);
        Assert.assertNotNull(loader);

        Task createdTask = taskManagementService.findTaskForLoader(LOADER_ID);
        Assert.assertNull(createdTask);
    }
}


