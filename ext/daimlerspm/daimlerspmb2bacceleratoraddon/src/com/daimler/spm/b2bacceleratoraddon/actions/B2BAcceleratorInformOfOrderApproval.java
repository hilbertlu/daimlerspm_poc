/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.daimler.spm.b2bacceleratoraddon.actions;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.process.approval.actions.InformOfOrderApproval;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.task.RetryLaterException;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;


/**
 * Sends an order approval email.
 */
public class B2BAcceleratorInformOfOrderApproval extends InformOfOrderApproval
{
	/**
	 * The Constant LOG.
	 */
	private static final Logger LOG = Logger.getLogger(InformOfOrderApproval.class);

	@Override
	public void executeAction(final B2BApprovalProcessModel process) throws RetryLaterException
	{
		OrderModel order = null;
		try
		{
			order = process.getOrder();
			Assert.notNull(order, String.format("Order of BusinessProcess %s should have be set for accelerator", process));
			final B2BCustomerModel user = (B2BCustomerModel) order.getUser();
			if (LOG.isDebugEnabled())
			{
				LOG.debug(String.format("Process for accelerator: %s in step %s order: %s user: %s ", process.getCode(), getClass(),
						order.getUnit(), user.getUid()));
			}
		}
		catch (final Exception exception)
		{
			LOG.error(exception.getMessage(), exception);
			this.handleError(order, exception);

			throw new IllegalStateException(exception.getMessage(), exception);
		}
	}

	protected void handleError(final OrderModel order, final Exception exception)
	{
		if (order != null)
		{
			this.setOrderStatus(order, OrderStatus.B2B_PROCESSING_ERROR);
		}
		LOG.error(exception.getMessage(), exception);
	}
}
