/**
 * Pre-requirements.
 * --Install readline (npm i readline)
 * --Install Prompt sync (npm install prompt-sync)
 * 
 * 
 * 
 * Generate A module
 * - Generate a module with the given name
 * - Generate dao
 * - Generate services
 * - Generate routes
 * - Generate utils
 * - Create a default submodule?
 * - Yes, create a default sub-module: (ask for name)
 * - Add Service
 *
 * - No, don't create a default submodule, types: blank, root page
 * - if blank: do nothing
 * - if root page: create a root page (container)
 *
 *
 * Generate a submodule
 * - Generate a submodule with the given name under a module
 * - Add services to parent module
 *
 *
 * Generate a component
 * - Generate a component with the given name under a module
 * - Type? Component, Modal
 *
 *
 * Generate a Filter, Pipe, Utility Function
 * - Generate a filter, pipe, utility function with the given name under a module
 */

const prompt = require("prompt-sync")();
const { exec } = require("child_process");


function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function createModule(moduleName){
    exec('ng g module modules/'+ moduleName, (error, stdout, stderr) => {
        if (error) {
            console.error(`error: ${error.message}`);
            return;
        }
        console.error(`stderr: ${stderr}`);
        console.log(`${stdout}`);
    });
}

function createDao(daoName){
    exec('ng g interface modules/' + daoName, (error, stdout, stderr) => {
        if (error) {
            console.error(`error: ${error.message}`);
            return;
        }
        console.error(`stderr: ${stderr}`);
        console.log(`${stdout}`);
    });
}

function createService(serviceName){
    exec('ng g service modules/' + serviceName, (error, stdout, stderr) => {
        if (error) {
            console.error(`error: ${error.message}`);
            return;
        }
        console.error(`stderr: ${stderr}`);
        console.log(`stdout:\n${stdout}`);
    });
}

function createRoutes(routesName){
    exec('ng g interface modules/' + routesName, (error, stdout, stderr) => {
        if (error) {
            console.error(`error: ${error.message}`);
            return;
        }
        console.error(`stderr: ${stderr}`);
        console.log(`stdout:\n${stdout}`);
    });
}

function createContainer(containerName){
    exec('ng g component --module modules/' + containerName, (error, stdout, stderr) => {
        if (error) {
            console.error(`error: ${error.message}`);
            return;
        }
        console.error(`stderr: ${stderr}`);
        console.log(`stdout:\n${stdout}`);
    });
}

function createComponent(componentName){
    exec('ng g component --module modules/' + componentName, (error, stdout, stderr) => {
        if (error) {
            console.error(`error: ${error.message}`);
            return;
        }
        console.error(`stderr: ${stderr}`);
        console.log(`stdout:\n${stdout}`);
    });
}


function Generate() {
    console.log("Please select one of the options below for create.\n");
    //await sleep(1000);
    console.log(" 1. Generate a module with the given name");
    console.log(" 2. Generate dao");
    console.log(" 3. Generate services");
    console.log(" 4. Generate routes");
    console.log(" 5. Generate Containers");
    console.log(" 6. Generate Components");
    console.log(" 7. Exit");

    var number = prompt("Enter your choice [1-5] : ");
    switch(number) {

        case '1':   
            var mod = prompt("Enter module name just like..(<module-name>): ");
            createModule(mod);
            var con = prompt("You want to create Sub-Module ? (yes/y or no/n) : ");
            console.log(con);
            console.log(con.toUpperCase());
            if(con.toUpperCase() === "YES" || con.toUpperCase() === "Y"){
                console.log("FU");
                var submoduleName = prompt("Enter Sub-Module name just like..(<module-name>): ");
                createModule(mod + "/" + submoduleName);
               // createComponent(mod  +"/"+submoduleName + " modules/" +mod + "/" + submoduleName +"/container");
            }else{
                return;
            }
            var serviceName =  mod + "/services/" + mod;
            createService(serviceName);
            var routesName = mod + "/routes/" + mod + " routes";
            createRoutes(routesName);
            createDao(mod + "/dao/" + mod + " models");
            createDao(mod + "/dao/" + mod + " data");
            createDao(mod + "/dao/" + mod + " headers");

            break;

        case '2':
            var dao = prompt("Enter Dao Name with path just like..(<module-name>/dao/<dao-name>): ");
            createDao(dao + " models");
            createDao(dao + " data");
            createDao(dao + " headers");
            break;

        case '3':
            var serviceName = prompt("Enter Service Name with path just like..(<module-name>/services/<service-name>): ");
            createService(serviceName);
            break;

        case '4':
            var routesName = prompt("Enter Routes Name with path just like..(<module-name>/routes/<routes-name>): ");
            createRoutes(routesName);
            break;

        case '5':
            var containerName = prompt("Enter Container Name with just like..(<module-name> modules/<module>/containers/<container-name>): ");
            createContainer(containerName);
            break;

        case '6':
            var componentName = prompt("Enter Component Name with path just like..(<module-name> modules/<module>/components/<component-name>): ");
            createComponent(componentName);
            break;

        case '7':
            console.log("\t---------------------------------------------------");
            console.log("\t|               Thanks For Coming                 |");
            console.log("\t---------------------------------------------------");
            break;
        default:
            console.log("PLEASE ENTER ONLY 1 TO 7");
            Generate();
        }
 }

 Generate();
