/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.rest.handler.job.checkpoints;

import org.apache.flink.api.common.time.Time;
import org.apache.flink.runtime.checkpoint.AbstractCheckpointStats;
import org.apache.flink.runtime.rest.handler.HandlerRequest;
import org.apache.flink.runtime.rest.handler.legacy.ExecutionGraphCache;
import org.apache.flink.runtime.rest.messages.EmptyRequestBody;
import org.apache.flink.runtime.rest.messages.MessageHeaders;
import org.apache.flink.runtime.rest.messages.checkpoints.CheckpointMessageParameters;
import org.apache.flink.runtime.rest.messages.checkpoints.CheckpointStatistics;
import org.apache.flink.runtime.webmonitor.RestfulGateway;
import org.apache.flink.runtime.webmonitor.retriever.GatewayRetriever;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * REST handler which returns the details for a checkpoint.
 */
public class CheckpointStatisticDetailsHandler extends AbstractCheckpointHandler<CheckpointStatistics, CheckpointMessageParameters> {

	public CheckpointStatisticDetailsHandler(
			CompletableFuture<String> localRestAddress,
			GatewayRetriever<? extends RestfulGateway> leaderRetriever,
			Time timeout,
			MessageHeaders<EmptyRequestBody, CheckpointStatistics, CheckpointMessageParameters> messageHeaders,
			ExecutionGraphCache executionGraphCache,
			Executor executor,
			CheckpointStatsCache checkpointStatsCache) {
		super(localRestAddress, leaderRetriever, timeout, messageHeaders, executionGraphCache, executor, checkpointStatsCache);
	}

	@Override
	protected CheckpointStatistics handleCheckpointRequest(HandlerRequest<EmptyRequestBody, CheckpointMessageParameters> ignored, AbstractCheckpointStats checkpointStats) {
		return CheckpointStatistics.generateCheckpointStatistics(checkpointStats, true);
	}
}
