# TestNG sample project

## How to use the project

- Replace the placeholders in `trcli-config.yml` with your TestRail instance details
- Execute the commands on the script below


# Install TR CLI
pip install trcli

# Install test project
mvn clean compile

# Run tests
mvn clean compile test
#--suite-id 25 --run-id 20
# Upload test results  
trcli -y -c "trcli-config.yml"  parse_junit  --suite-id 32 --run-id 27



