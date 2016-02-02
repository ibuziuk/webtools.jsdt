package org.eclipse.wst.jsdt.js.grunt.internal.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;

/**
 * Utility class to handle image resources.
 */
public class ImageResource {
	private static ImageRegistry imageRegistry;
	private static Map<String, ImageDescriptor> imageDescriptors;
	private static URL ICON_BASE_URL;
	
	//TODO: DESIGN-735 Need to create icon for JavaScript Build Systems
	public static final String IMG_GRUNTFILE = "grunt_16.png"; //$NON-NLS-1$

	static {
		try {
			String pathSuffix = "icons/"; //$NON-NLS-1$
			ICON_BASE_URL = GruntPlugin.getDefault().getBundle()
					.getEntry(pathSuffix);
		} catch (Exception e) {
			GruntPlugin.logError(e , "Images error: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	/**
	 * Return the image with the given key.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.swt.graphics.Image
	 */
	public static Image getImage(String key) {
		return getImage(key, null);
	}

	/**
	 * Return the image with the given key.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.swt.graphics.Image
	 */
	public static Image getImage(String key, String keyIfImageNull) {
		if (imageRegistry == null)
			initializeImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null) {
			if (keyIfImageNull != null) {
				return getImage(keyIfImageNull, null);
			}
			imageRegistry.put(key, ImageDescriptor.getMissingImageDescriptor());
			image = imageRegistry.get(key);
		}
		return image;
	}

	/**
	 * Return the image descriptor with the given key.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.jface.resource.ImageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		if (imageRegistry == null)
			initializeImageRegistry();
		ImageDescriptor id = imageDescriptors.get(key);
		if (id != null)
			return id;

		return ImageDescriptor.getMissingImageDescriptor();
	}

	/**
	 * Initialize the image resources.
	 */
	protected static void initializeImageRegistry() {
		imageRegistry = GruntPlugin.getDefault().getImageRegistry();
		imageDescriptors = new HashMap<String, ImageDescriptor>();
		registerImage(IMG_GRUNTFILE, IMG_GRUNTFILE);
	}

	/**
	 * Register an image with the registry.
	 * 
	 * @param key
	 *            java.lang.String
	 * @param partialURL
	 *            java.lang.String
	 */
	public static void registerImage(String key, String partialURL) {
		try {
			ImageDescriptor id = ImageDescriptor.createFromURL(new URL(
					ICON_BASE_URL, partialURL));
			registerImageDescriptor(key, id);
		} catch (Exception e) {
			GruntPlugin.logError(e , "Error registering image" + e.getMessage()); //$NON-NLS-1$
		}
	}

	public static void registerImageDescriptor(String key, ImageDescriptor id) {
		if (imageRegistry == null)
			initializeImageRegistry();
		imageRegistry.put(key, id);
		imageDescriptors.put(key, id);
	}

}