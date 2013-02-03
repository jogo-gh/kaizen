package kaizen.plugins.clr

import kaizen.commons.Paths
import org.apache.commons.lang3.SystemUtils

interface FrameworkLocator {
	String getFrameworkPath(String frameworkName)
}

class MonoFramework {
	FrameworkLocator locator
	String frameworkName
	MonoTool gmcs = new MonoTool(this, 'gmcs')
	MonoTool booc = new MonoTool(this, 'booc')

	MonoFramework(FrameworkLocator locator, String frameworkName) {
		this.locator = locator
		this.frameworkName = frameworkName
	}

	String getCli() {
		monoExecutable("cli")
	}

	String monoExecutable(String name) {
		platformSpecificExecutable monoBinPath(name)
	}

	String monoLib(String relativePath) {
		Paths.combine monoPath, 'lib', 'mono', '2.0', relativePath
	}

	String platformSpecificExecutable(String executable) {
		SystemUtils.IS_OS_WINDOWS ? "${executable}.bat" : executable
	}


	String monoBinPath(String path) {
		Paths.combine monoPath, "bin", path
	}

	String getMonoPath() {
		locator.getFrameworkPath(frameworkName)
	}
}

class MonoTool {
	final MonoFramework framework
	final String name
	def executable

	MonoTool(MonoFramework framework, String name) {
		this.framework = framework
		this.name = name
	}

	String getExecutable() {
		executable ?: framework.monoLib("${name}.exe")
	}
}
