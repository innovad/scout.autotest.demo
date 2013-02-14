/**
 *
 */
package org.eclipse.autotest.client.test;


/**
 * @author amo
 */
public interface IClientTestEnvironment {

  void setupGlobalEnvironment();

  void setupInstanceEnvironment();

}
