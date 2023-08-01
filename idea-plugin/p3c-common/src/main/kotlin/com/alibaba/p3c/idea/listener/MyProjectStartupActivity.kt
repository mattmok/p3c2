package com.alibaba.p3c.idea.listener

import com.alibaba.p3c.idea.compatible.inspection.Inspections
import com.alibaba.p3c.idea.config.P3cConfig
import com.alibaba.p3c.idea.i18n.P3cBundle
import com.alibaba.p3c.idea.service.FileListenerService
import com.alibaba.p3c.pmd.I18nResources
import com.alibaba.smartfox.idea.common.util.getService
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.ProjectActivity

class MyProjectStartupActivity : ProjectActivity, ProjectManagerListener {

    private val p3cConfig = P3cConfig::class.java.getService()

    override suspend fun execute(project: Project) {
        I18nResources.changeLanguage(p3cConfig.locale)
        val analyticsGroup = ActionManager.getInstance().getAction(analyticsGroupId)
        analyticsGroup.templatePresentation.text = P3cBundle.getMessage(analyticsGroupText)
        Inspections.addCustomTag(project, "date")
        val fileService = project.getService(FileListenerService::class.java)
        fileService.projectOpened(project)
    }

    override fun projectClosed(project: Project) {
        val fileService = project.getService(FileListenerService::class.java)
        fileService.projectClosed(project)
    }

    companion object {
        const val analyticsGroupId = "com.alibaba.p3c.analytics.action_group"
        const val analyticsGroupText = "$analyticsGroupId.text"
    }

}