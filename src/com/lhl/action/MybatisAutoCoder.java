package com.lhl.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

/**
 * @author Liu Hailin
 * @create 2017-12-05 下午9:58
 **/
public class MybatisAutoCoder extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent e) {

        final String projectPath = e.getProject().getBasePath();

        final Project project = e.getData( PlatformDataKeys.PROJECT );

        final Editor editor = e.getData( PlatformDataKeys.EDITOR );

        // TODO: insert action logic here
        AutoCoderDialog.main( null );
    }
}
