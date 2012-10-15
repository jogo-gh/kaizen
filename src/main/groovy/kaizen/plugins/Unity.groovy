package kaizen.plugins

import org.gradle.api.Project

import kaizen.foundation.SystemInformation
import kaizen.foundation.Paths

class Unity implements FrameworkLocator {

	def unityDir

	final MonoFramework mono

	final MonoFramework monoBleedingEdge

	final Project project

	Unity(Project project) {
		this.project = project
		this.unityDir = defaultUnityLocation()
		this.mono = new MonoFramework(this, 'Mono')
		this.monoBleedingEdge = new MonoFramework(this, 'MonoBleedingEdge')
	}

	@Override
	String getFrameworkPath(String frameworkName) {
		def frameworksPath = SystemInformation.isMac() ? 'Contents/Frameworks' : 'Data'
		Paths.combine absoluteUnityDir(), frameworksPath, frameworkName
	}

	def defaultUnityLocation() {
		'/Applications/Unity/Unity.app'
	}

	private absoluteUnityDir() {
		project.file(configuredUnityDir()).absolutePath
	}

	private configuredUnityDir() {
		project.hasProperty('unityDir') ? project.property('unityDir') : unityDir
	}
}