package com.sw.base.controller.system;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.system.JobServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.JobConstants;
import com.sw.common.entity.system.Job;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/system-web/job")
public class JobController extends BaseController<JobServiceImpl,Job> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    JobServiceImpl jobService;

    @Override
    @ResponseBody
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public DataResponse update(Job job) {
        if(job == null){
            return DataResponse.fail("参数为空，更新失败");
        }
        super.update(job);
        DataResponse dataResponse = super.get(job.getPkJobId());
        Job oldJob = (Job) dataResponse.get("obj");
        if(oldJob == null){
            return DataResponse.fail("调度任务不能为空!");
        }
        if(!oldJob.getCorn().equals(job.getCorn())){
            // 更新cron表达式
            try {
                jobService.updateCron(job);
            } catch (SchedulerException e) {
                return DataResponse.fail(e.getMessage());
            }
        }
        return DataResponse.success();
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public DataResponse updateStatus(@RequestBody Map<String, Object> params) throws SchedulerException {
        log.info("开始更新调度任务状态");
        String flag = MapUtil.getMapValue(params, "flag");
        String id = MapUtil.getMapValue(params, "id");
        Job job;
        if(StringUtil.isNotEmpty(id)){
            DataResponse dataResponse = super.get(id);
            job = (Job) dataResponse.get("obj");
            if(job == null){
                return DataResponse.fail("调度任务不能为空!");
            }
            params.put("job", job);
        }else{
            return DataResponse.fail("调度任务不能为空!");
        }

        jobService.updateStatus(params);

        if(JobConstants.JOB_START.equals(flag)){
            job.setStatus(JobConstants.JOB_START_DICT);
        }else{
            job.setStatus(JobConstants.JOB_STOP_DICT);
        }

        super.update(job);

        return DataResponse.success();
    }
}