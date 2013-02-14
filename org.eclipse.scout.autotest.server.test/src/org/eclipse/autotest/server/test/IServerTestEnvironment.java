/**
 *
 */
package org.eclipse.autotest.server.test;


/**
 * @author amo
 */
public interface IServerTestEnvironment {

  void setupGlobalEnvironment();

  void setupInstanceEnvironment();

}
