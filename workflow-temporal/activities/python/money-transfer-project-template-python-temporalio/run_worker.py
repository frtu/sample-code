# @@@SNIPSTART python-money-transfer-project-template-run-worker
import asyncio

from temporalio.client import Client
from temporalio.worker import Worker

from activities import BankingActivities
from shared import MONEY_TRANSFER_TASK_QUEUE_NAME


async def main() -> None:
    client: Client = await Client.connect("localhost:7233", namespace="default")
    # Run the worker
    activities = BankingActivities()
    worker: Worker = Worker(
        client,
        task_queue=MONEY_TRANSFER_TASK_QUEUE_NAME,
        activities=[activities.Withdraw, activities.Deposit, activities.Refund],
    )
    await worker.run()


if __name__ == "__main__":
    asyncio.run(main())
# @@@SNIPEND
