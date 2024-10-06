# Project - activities-python

## About

Create activities for python.

## Technical details

### `temporalio` package

Add [temporalio](https://pypi.org/project/temporalio/) package to `pyproject.toml` :

```
temporalio = "^1.7.1"
```

## Instalation

### Poetry command

[Install poetry](https://github.com/python-poetry/install.python-poetry.org?tab=readme-ov-file#usage)

```
curl -sSL https://install.python-poetry.org | python3 -
```

Check installation using `poetry --version`

### Install project

* Run `poetry config virtualenvs.in-project` to check `virtualenvs.create = true`. List [all configuration here](https://python-poetry.org/docs/configuration/#listing-the-current-configuration)
* Run `poetry install`
* Activate using `source $(poetry env info --path)/bin/activate`. [Manage environment using this link](https://python-poetry.org/docs/managing-environments/)
* Start service using `poetry run python run_worker.py`

See more with https://medium.com/@mronakjain94/comprehensive-guide-to-installing-poetry-on-ubuntu-and-managing-python-projects-949b49ef4f76

## About

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Initial version