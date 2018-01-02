export default {
  items: [

    {
      name: 'Dashboard',
      url: '/admin/',
      icon: 'icon-home',

    },

    {
      name: 'Issues',
      url: '/admin/issues',
      icon: 'icon-layers',
      badge: {
        variant: 'info',
        text: 'NEW'
      }
    },

    {
      name: 'Petitions',
      url: '/admin/petitions',
      icon: 'icon-list',
      badge: {
        variant: 'info',
        text: 'NEW'
      }
    },

    {
      name: 'Events',
      url: '/admin/events',
      icon: 'icon-event',
      badge: {
        variant: 'info',
        text: 'NEW'
      }
    },

    {
      name: 'Users',
      url: '/admin/users',
      icon: 'icon-people',
      badge: {
        variant: 'info',
        text: 'NEW'
      }
    },

    {
      name: 'Settings',
      url: '/admin/settings',
      icon: 'icon-wrench',
    },

  ]
};
