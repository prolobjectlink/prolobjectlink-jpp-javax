/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package org.prolobjectlink.web.platform;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.prolobjectlink.db.DatabaseServer;

import io.github.prolobjectlink.prolog.ArrayIterator;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractWebControl extends AbstractWebPlatform implements WebServerControl {

	private final WebServer webServer;
	private final DatabaseServer databaseServer;

	// standard output stream
	// private final PrintWriter stdout = System.console().writer()
	private static final PrintWriter stdout = new PrintWriter(System.out, true);

	public AbstractWebControl(WebServer webServer, DatabaseServer databaseServer) {
		this.databaseServer = databaseServer;
		this.webServer = webServer;
	}

	public final DatabaseServer getDatabaseServer() {
		return databaseServer;
	}

	public final WebServer getWebServer() {
		return webServer;
	}

	public final Map<String, String> getArguments(String[] args) {
		final Map<String, String> map = new HashMap<String, String>();
		if (args.length > 0) {
			Iterator<String> i = new ArrayIterator<String>(args);
			String name = i.next();
			if (i.hasNext()) {
				String value = i.next();
				map.put(name, value);
			} else {
				map.put(name, "");
			}
		}
		return map;
	}

	public final void printUsage() {
		stdout.println("Usage: prolog option [file] to consult a file");
		stdout.println("options:");
		stdout.println("	-r	consult/run a prolog file");
		stdout.println("	-v	print the prolog engine version");
		stdout.println("	-n	print the prolog engine name");
		stdout.println("	-l	print the prolog engine license");
		stdout.println("	-i	print the prolog engine information");
		stdout.println("	-a	print the prolog engine about");
		stdout.println("	-e	print the prolog engine enviroment paths");
		stdout.println("	-x	start the prolog engine execution");
		stdout.println("	-w	print the current work directory ");
		stdout.println("	-f	consult a prolog file and save formatted code");
	}

	public final void openBrowser(String url) {

		if (runOnLinux()) {

			// See if the default browser is Konqueror by resolving the symlink.
			boolean isDefaultKonqueror = false;
			try {
				// Find out the location of the x-www-browser link from path.
				Process process = runtime.exec("which x-www-browser");
				BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
				BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
				String defaultLinkPath = bufreader.readLine();
				ins.close();

				// The path is null if the link did not exist.
				if (defaultLinkPath != null) {
					// See if the default browser is Konqueror.
					File file = new File(defaultLinkPath);
					String canonical = file.getCanonicalPath();
					if (canonical.indexOf("konqueror") != -1) {
						isDefaultKonqueror = true;
					}
				}
			} catch (IOException e1) {
				// The symlink was probably not found, so this is ok.
			}

			// Try x-www-browser, which is symlink to the default browser,
			// except if we found that it is Konqueror.
			if (!started && !isDefaultKonqueror) {
				try {
					runtime.exec("x-www-browser " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

			// Try firefox
			if (!started) {
				try {
					runtime.exec("firefox " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

			// Try mozilla
			if (!started) {
				try {
					runtime.exec("mozilla " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

			// Try konqueror
			if (!started) {
				try {
					runtime.exec("konqueror " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

		} else if (runOnOsX()) {
			if (!started) {
				try {
					runtime.exec("open " + url);
					started = true;
				} catch (final IOException e) {
				}
			}
		} else if (runOnWindows()) {

			if (!started) {
				try {
					runtime.exec("cmd /c start " + url);
					started = true;
				} catch (final IOException e) {
				}
			}

		}

	}

	public final void start() {
		webServer.start();
	}

	public final void restart() {
		webServer.restart();
	}

	public final void stop() {
		webServer.stop();
	}

	public final void run(String[] args) {

		final String url = "http://localhost:" + webServer.getPort() + "/home";
		final Map<String, String> serverArgs = getArguments(args);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
//				databaseServer.shutdown();
				webServer.stop();
			}
		});

		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("trayIcon.png");

			PopupMenu menu = new PopupMenu();

			MenuItem openItem = new MenuItem("Explorer");
			openItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openBrowser(url);
				}
			});
			menu.add(openItem);

			MenuItem startItem = new MenuItem("Start");
			startItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (runOnWindows()) {
							runtime.exec(getBinDirectory().getCanonicalPath() + "/" + "start.bat");
						} else {
							runtime.exec(getBinDirectory().getCanonicalPath() + "/" + "start.sh");
						}
					} catch (IOException e1) {
						Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					}
				}
			});
			menu.add(startItem);
			
			MenuItem restartItem = new MenuItem("Restart");
			restartItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (runOnWindows()) {
							runtime.exec(getBinDirectory().getCanonicalPath() + "/" + "restart.bat");
						} else {
							runtime.exec(getBinDirectory().getCanonicalPath() + "/" + "restart.sh");
						}
					} catch (IOException e1) {
						Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					}
				}
			});
			menu.add(restartItem);

			MenuItem stopItem = new MenuItem("Stop");
			stopItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (runOnWindows()) {
							runtime.exec(getBinDirectory().getCanonicalPath() + "/" + "stop.bat");
						} else {
							runtime.exec(getBinDirectory().getCanonicalPath() + "/" + "stop.sh");
						}
					} catch (IOException e1) {
						Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
					}
				}
			});
			menu.add(stopItem);

			MenuItem configItem = new MenuItem("Config.");
			startItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Configuration");
				}
			});
			menu.add(configItem);

			MenuItem helpItem = new MenuItem("Help");
			helpItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Help");
				}
			});
			menu.add(helpItem);

			MenuItem aboutItem = new MenuItem("About");
			aboutItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "About");
				}
			});
			menu.add(aboutItem);

			MenuItem closeItem = new MenuItem("Close");
			closeItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			menu.add(closeItem);

			TrayIcon icon = new TrayIcon(image, "Prolobjectlink Server", menu);
			icon.setImageAutoSize(true);
			try {
				tray.add(icon);
			} catch (AWTException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			}

		}

	}

}
