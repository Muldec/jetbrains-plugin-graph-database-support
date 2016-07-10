package com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state;

import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.DataSourceType;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state.impl.DataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSourcesComponentStateTest {

    private DataSourcesComponentState state;

    @Before
    public void setUp() throws Exception {
        state = new DataSourcesComponentState();
    }

    @Test
    public void testMigrateFromInitial() throws Exception {
        state.dataSources.add(new DataSource(DataSourceType.NEO4J_BOLT, "testName", Collections.emptyMap()));
        state.migrate();

        List<DataSourceApi> dataSources = state.containerV1.getDataSources();
        assertThat(state.dataSources).hasSize(0);
        assertThat(dataSources).hasSize(1);
        DataSourceApi dataSource = dataSources.get(0);

        assertThat(dataSource.getUUID())
                .isNotNull()
                .hasSameSizeAs(UUID.randomUUID().toString());
        assertThat(dataSource.getName())
                .isEqualTo("testName");
        assertThat(dataSource.getDataSourceType())
                .isEqualTo(DataSourceType.NEO4J_BOLT);
    }

    @Test(expected = IllegalStateException.class)
    public void findDataSourceByUuidThrowsExceptionIfNotExists() throws Exception {
        state.getCurrentContainer().findDataSource("notexists");
    }

    @Test
    public void findDataSourceByUuidReturnObjectIfExists() throws Exception {
        DataSourceApi dataSource = state.getCurrentContainer()
                .createDataSource(null, DataSourceType.NEO4J_BOLT, "testName", Collections.emptyMap());

        state.getCurrentContainer().addDataSource(dataSource);
        DataSourceApi foundDataSource = state.getCurrentContainer().findDataSource(dataSource.getUUID());

        assertThat(foundDataSource.getUUID()).isEqualTo(dataSource.getUUID());
        assertThat(foundDataSource.getName()).isEqualTo(dataSource.getName());
    }
}
