package com.kramrs.quartz.execution;

import com.kramrs.entity.Task;
import com.kramrs.quartz.utils.TaskInvokeUtils;
import org.quartz.JobExecutionContext;

/**
 * @Author: kramrs
 * @Description: 定时任务处理（允许并发执行）
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, Task task) throws Exception {
        TaskInvokeUtils.invokeMethod(task);
    }
}
