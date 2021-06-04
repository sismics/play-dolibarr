[![GitHub release](https://img.shields.io/github/release/sismics/play-dolibarr.svg?style=flat-square)](https://github.com/sismics/play-dolibarr/releases/latest)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# play-dolibarr plugin

This plugin adds [Dolibarr](https://www.dolibarr.org/) support to Play! Framework 1 applications.

# Features

# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - dolibarr -> dolibarr 1.8.0

repositories:
    - sismicsNexusRaw:
        type: http
        artifact: "https://nexus.sismics.com/repository/sismics/[module]-[revision].zip"
        contains:
            - dolibarr -> *

```
####  Set configuration parameters

Add the following parameters to **application.conf**:

```
# Dolibarr configuration
# ~~~~~~~~~~~~~~~~~~~~
dolibarr.mock=false
dolibarr.api.url=https://dolibarr.example.com/api/index.php
dolibarr.dolapikey=12345678
```
####  Use the API

```
List<BankAccount> accounts = DolibarrClient.get().getBankAccountService().listBankAccount();
```

####  Mock the Dolibarr server in dev

We recommand to mock Dolibarr in development mode and test profile.

Use the following configuration parameter:

```
dolibarr.mock=true
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
