package ilyatkachev.github.com.backend;

import com.example.Result;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CalculatorTest {

    @Mock
    private BackendCalculator mBackendCalculator;
    @Mock
    List<Result> mList;
    @Captor
    private ArgumentCaptor<Result> mCaptor;

    @Before
    public void setUp() {
        mBackendCalculator = spy(BackendCalculator.class);
        mCaptor = ArgumentCaptor.forClass(Result.class);
        mList = mock(ArrayList.class);
    }

    @Test
    public void simpleDoReturnTest() {
        doReturn("Any result").when(mBackendCalculator).evaluate(anyString());
        verify(mBackendCalculator,never()).evaluate(anyString());
        assertEquals(mBackendCalculator.evaluate(anyString()),"Any result");
    }

    @Test
    public void simpleCaptorTest() {
        Result a = new Result();
        a.setResult(12345f);
        a.setError("No errors!");
        mList.add(a);
        verify(mList).add(mCaptor.capture());
        assertEquals(12345f, mCaptor.getValue().getResult(),0);
    }

}
