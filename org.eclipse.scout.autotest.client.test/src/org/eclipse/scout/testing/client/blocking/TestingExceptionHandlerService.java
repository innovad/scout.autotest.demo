package org.eclipse.scout.testing.client.blocking;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.shared.services.common.exceptionhandler.IExceptionHandlerService;
import org.eclipse.scout.rt.testing.shared.services.common.exceptionhandler.WrappedProcessingRuntimeException;
import org.eclipse.scout.service.AbstractService;

public class TestingExceptionHandlerService extends AbstractService implements IExceptionHandlerService {
  private ProcessingException m_lastException;
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(TestingExceptionHandlerService.class);

  @Override
  public void handleException(ProcessingException t) {
    if (t != null) {
      LOG.warn("consuming: " + t.toString());
    }
    m_lastException = t;
  }

  public void assertNoException() throws ProcessingException {
    if (m_lastException == null) {
      return;
    }

    if (m_lastException.getCause() instanceof AssertionError) {
      throw (AssertionError) m_lastException.getCause();
    }
    else if (m_lastException.getCause() instanceof WrappedProcessingRuntimeException) {
      WrappedProcessingRuntimeException ex = (WrappedProcessingRuntimeException) m_lastException.getCause();
      if (ex.getCause() != null && ex.getCause() instanceof ProcessingException) {
        throw (ProcessingException) ex.getCause();
      }
    }

    throw m_lastException;
  }

  public void clear() {
    m_lastException = null;
  }
}
