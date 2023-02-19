package com.tulioperezalgaba.wixsite;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewModelSchoolListTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RepoSchools repoSchools = Mockito.mock(RepoSchools.class);
    private ViewModelSchoolList viewModelSchoolList = new ViewModelSchoolList();

    @Test
    public void loadSchools_success() {
        // Given
        List<ModelSchools> schools = new ArrayList<>();
        List<ModelSATScores> satScores = new ArrayList<>();

        satScores.add(new ModelSATScores(400, 400, 400));
        schools.add(new ModelSchools("DBN1", "School Name 1", satScores));
        schools.add(new ModelSchools("DBN2", "School Name 2", satScores));
        schools.add(new ModelSchools("DBN3", "School Name 3", satScores));

        // When
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback<List<ModelSchools>> callback = invocation.getArgument(0);
                callback.onResponse(null, Response.success(schools));
                return null;
            }
        }).when(repoSchools).getSchools(Mockito.any(Callback.class));

        // Then
        viewModelSchoolList.loadSchools();
        assertEquals(schools, viewModelSchoolList.getSchoolsLiveData().getValue());
        assertNull(viewModelSchoolList.getErrorLiveData().getValue());
    }

    @Test
    public void loadSchools_failure() {
        // Given
        String errorMessage = "Error fetching schools data";

        // When
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback<List<ModelSchools>> callback = invocation.getArgument(0);
                callback.onResponse(null, Response.error(404, ResponseBody.create(MediaType.parse("application/json"), "Not found")));
                return null;
            }
        }).when(repoSchools).getSchools(Mockito.any(Callback.class));

        // Then
        viewModelSchoolList.loadSchools();
        assertNull(viewModelSchoolList.getSchoolsLiveData().getValue());
        assertEquals(errorMessage + ": Not found", viewModelSchoolList.getErrorLiveData().getValue());
    }

}

