package com.sw.base.service.impl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.constants.JobConstants;
import com.sw.common.entity.SchedulerJob;
import com.sw.common.util.JobUtil;
import com.sw.common.util.MapUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.system.Job;
import com.sw.base.mapper.system.JobMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 调度任务表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-17 12:07:34
 */
@Service("jobService")
public class JobServiceImpl extends BaseService<JobMapper,Job> {

    @Autowired
    JobMapper jobMapper;

    @Autowired
    JobUtil jobUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    /**
     * 初始化所有启用的调度
     */
    public void initSchedule() {

        EntityWrapper<Job> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);

        List<Job> list = jobMapper.selectList(wrapper);

        if(!CollectionUtils.isEmpty(list)){
            for(Job job:list){
                if("SW1302".equals(job.getStatus())){
                    SchedulerJob schedulerJob = jobUtil.jobToSchedulerJob(job);
                    jobUtil.addJob(schedulerJob);
                }
            }
        }
    }

    /**
     * 更新调度状态
     * @param params
     * @throws SchedulerException
     */
    public void updateStatus(Map<String, Object> params) throws SchedulerException {
        String flag = MapUtil.getMapValue(params, "flag");
        Job job = (Job) params.get("job");
        SchedulerJob schedulerJob = jobUtil.jobToSchedulerJob(job);
        if(schedulerJob == null){
            return;
        }
        if(JobConstants.JOB_START.equals(flag)){
           schedulerJob.setJobStatus(JobConstants.JOB_START_DICT);
           jobUtil.addJob(schedulerJob);
        } else {
            jobUtil.deleteJob(schedulerJob);
        }
    }

    /**
     * 更新调度任务cron表达式
     * @param job
     */
    public void updateCron(Job job) throws SchedulerException {
        jobUtil.updateJobCron(jobUtil.jobToSchedulerJob(job));
    }
}