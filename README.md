# What is AREX's config service?
1. The service provide web APIs to config the AREX working, such as: response diff,record collecting,replay send max
 qps.
1. init or sync replay operations called by the `/api/config/agent/load` when the target agent starting.
