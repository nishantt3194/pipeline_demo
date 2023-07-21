/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import { CoreMenu } from "@core/types";

export const menu: CoreMenu[] = [
  {
    id: "core",
    title: "Core",
    type: "section",
    children: [
      {
        id: "dashboard",
        title: "Dashboard",
        type: "item",
        icon: "outline_widget",
        customIcon: true,
        url: "/dashboard/overview",
      },
    ],
  },
  {
    id: "manage",
    title: "Manage",
    type: "section",
    children: [
      {
        id: "machines",
        title: "Machines",
        type: "item",
        icon: "outline_transport_spedometer_max",
        customIcon: true,
        url: "/machines/list",
      },
      {
        id: "entries",
        title: "Entries",
        type: "item",
        icon: "oee",
        customIcon: true,
        url: "/entries/list",
      },

      {
        id: "products",
        title: "Products",
        type: "item",
        role: ["ADMINISTRATOR"],
        icon: "outline_medicine_syringe",
        customIcon: true,
        url: "/products/list",
      },
    ],
  },
  {
    id: "platform",
    title: "Platform",
    type: "section",
    role: ["ADMINISTRATOR", "SUPERVISOR"],
    children: [
      {
        id: "users",
        title: "Users",
        type: "item",
        role: ["ADMINISTRATOR", "SUPERVISOR"],
        icon: "outline_users_group_rounded",
        customIcon: true,
        url: "/users/list",
      },
      // {
      //   id: "logs",
      //   title: "Activity",
      //   type: "item",
      //   icon: "outline_notes_documents",
      //   customIcon: true,
      //   url: "/logs/list",
      // },
    ],
  },
  {
    id: "settings",
    title: "Settings",
    type: "section",
    children: [
      {
        id: "area",
        title: "Area",
        type: "item",
        icon: "map",
        role: ["ADMINISTRATOR", "SUPERVISOR"],
        customIcon: true,
        url: "/settings/area",
      },
      {
        id: "shift",
        title: "Shifts",
        type: "item",
        role: ["ADMINISTRATOR", "SUPERVISOR"],
        icon: "outline_tuning_square_2",
        customIcon: true,
        url: "/settings/shift",
      },
      {
        id: "downtimes",
        title: "Downtime",
        type: "item",
        role: ["ADMINISTRATOR", "SUPERVISOR"],
        icon: "outline_users_group_rounded",
        customIcon: true,
        url: "/settings/downtimes",
      },
      {
        id: "holidays",
        title: "Holidays",
        type: "item",
        // role: ["ADMINISTRATOR", "SUPERVISOR"],
        icon: "calendar",
        customIcon: true,
        url: "/settings/holidays",
      },
    ],
  },
];
