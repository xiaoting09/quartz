/* 
 * Copyright 2005 - 2009 Terracotta, Inc. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.quartz.examples.example6;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.StatefulJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p>
 * A job dumb job that will throw a job execution exception
 * </p>
 * 
 * @author Bill Kratzer
 */
public class BadJob2 implements StatefulJob {

    // Logging
    private static Log _log = LogFactory.getLog(BadJob2.class);

    /**
     * Empty public constructor for job initilization
     */
    public BadJob2() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException
     *           if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        String jobName = context.getJobDetail().getFullName();
        _log.info("---" + jobName + " executing at " + new Date());

        // a contrived example of an exception that
        // will be generated by this job due to a 
        // divide by zero error
        try {
            int zero = 0;
            int calculation = 4815 / zero;
        } catch (Exception e) {
            _log.info("--- Error in job!");
            JobExecutionException e2 = 
                new JobExecutionException(e);
            // Quartz will automatically unschedule
            // all triggers associated with this job
            // so that it does not run again
            e2.setUnscheduleAllTriggers(true);
            throw e2;
        }

        _log.info("---" + jobName + " completed at " + new Date());
    }

}