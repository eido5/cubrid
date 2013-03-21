/*
 * Copyright (C) 2008 Search Solution Corporation. All rights reserved by Search Solution.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

#ifndef HOST_H_
#define HOST_H_

namespace dbgw
{

  class _Host
  {
  public:
    _Host(const char *szUrl, const char *szUser, const char *szPassword,
        int nWeight);
    virtual ~ _Host() {}

    void setAltHost(const char *szAddress, const char *szPort);

  public:
    std::string getUrl() const;
    const char *getUser() const;
    const char *getPassword() const;
    int getWeight() const;

  private:
    std::string m_url;
    std::string m_althost;
    std::string m_user;
    std::string m_password;
    int m_nWeight;
  };

}

#endif
